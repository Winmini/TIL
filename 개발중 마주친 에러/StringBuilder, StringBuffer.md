# StringBuilder, StringBuffer



자바를 공부하다 보면, 면접 질문으로 자주 나온다는 것 중 하나이다.

바로, `String`, `StringBuilder`, `StringBuffer` 의 차이점에 대해서 아느냐는 것이다. cs공부하면서 습관적으로 외웠던 기억이 있다. 그리고 개발자라면 이렇게 cs로 외워진 지식은 코딩 공부에 비해 빠르게 휘발된다. 코드로 직접 쳐볼 일이 없기 때문이다.

나도 String은 불변객체이고 나머지는 가변객체임은 알고 있었지만 어느 것이 멀티스레드에 안전하다라고만 알고 있을 뿐, 잘 외워지진 않았다. 실제로 접하기 전까진..

멀티쓰레드, 비동기 통신으로 여러 api에 동시 접근을 하기 위하여 코드를 개발하던 중 아무 생각 없이 `StringBuilder`를 사용하였다. String 연산은 상당히 느리기 때문에 자연스레 `Builder`를 골랐고, 결과는 참사가 났다.

먼저 멀티 쓰레드를 이용한 코드는 다음과 같다.

```java
@Override
public List<Store> searchStores(ApiQuery apiQuery) {

  List<ApiQuery> apiQueries = Arrays.stream(OpenApiAddress.values())
    .map(openApiAddress -> new ApiQuery(apiQuery.getQueryString(), openApiAddress))
    .collect(toList());

  List<CompletableFuture<List<Store>>> collect = apiQueries.stream()
    .map(query -> CompletableFuture.supplyAsync(() -> openApi.getData(query), executor))
    .collect(toList());

  return collect.stream()
    .map(CompletableFuture::join)
    .flatMap(Collection::stream)
    .collect(toList());
}
```

I/O는 대기시간이 긴 만큼, 커스터마이징을 할 수 있는 `executor`를 사용하였다. 각각의 스레드는 I/O를 보내고 다 완성되면 join을 하여 결과를 내 뱉을 것이다.

그런데 API 마다 데이터의 양이 다르다보니 `perPage` 는 그때 그때 맞는 걸 계산하여 보내게 짜뒀다. 그리고 객체마다 각각 `StringBuilder`라는 객체를 각각 보유하고 있으므로, 멀티쓰레드에서 문제가 된다는 아무런 생각조차 하지 않고 보냈다.

그리고 다음과 같은 결과를 얻었다.

```java
INFO 4712 --- [Thread-9] c.c.n.m.service.openapi.OpenApiTemplate  : apiQuery.getUrl()=https://api.odcloud.kr/api?page=1&perPage=0&perPage=0
INFO 4712 --- [Thread-7] c.c.n.m.service.openapi.OpenApiTemplate  : apiQuery.getUrl()=https://api.odcloud.kr/api?page=1D&perPage=0
INFO 4712 --- [Thread-8] c.c.n.m.service.openapi.OpenApiTemplate  : apiQuery.getUrl()=https://api.odcloud.kr/api?page=1&perPage=0&perPage=0&perPage=0
```

처음엔 다 다른 객체인데 도대체 왜 이런 문제가 생겼지,, 생각하다가 예전 cs공부할 때 멀티쓰레드에 안전하지 않다는 생각이 잠깐 스쳤다.

쓰레드는 전부 다르므로 멀티쓰레드이고 perPage가 1개부터 3개까지 붙어 있다.

왜냐하면 StringBuilder는 힙 메모리에 속해있으며, 쓰레드가 각각 같은 것을 가리키고 있다. 동시에 가변 객체에 접근하여 값을 변경하다보니  데이터의 일관성이 깨진 것이다.

때문에 이런 가변 환경에서는 `StringBuffer`를 사용해야 한다.

cs공부하다가 그저 외우고 지나갔던 것을 직접 경험해보아 다신 잊지 않을 것이다..