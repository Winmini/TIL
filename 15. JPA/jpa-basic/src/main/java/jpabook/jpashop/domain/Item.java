package jpabook.jpashop.domain;

import static javax.persistence.InheritanceType.*;

import javax.naming.ldap.PagedResultsControl;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn
public abstract class Item extends BaseEntity{

	@Id @GeneratedValue
	private Long id;

	private String name;
	private int price;

}
