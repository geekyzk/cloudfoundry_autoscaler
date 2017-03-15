package com.em248.cloudfoundry.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * service的信息实体类
 * 
 * @author tian
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@Entity
public class ServiceDefinition {

	@JsonSerialize
	@JsonProperty("id")
	@Id
	private String id = UUID.randomUUID().toString();
	
	@JsonSerialize
	@JsonProperty("name")
	@Column(name="name")
	private String name;
	
	@JsonSerialize
	@JsonProperty("description")
	@Column(name="description")
	private String description;
	
	@JsonSerialize
	@JsonProperty("bindable")
	@Column(name="bindable")
	private boolean bindable;
	
	@JsonSerialize
	@JsonProperty("plans")
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="service_definition_id")
	private Set<Plan> plans = new HashSet();
	
	@JsonSerialize
	@JsonProperty("tags")
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(
	        name="service_tags",
	        joinColumns=@JoinColumn(name="sid")
	  )
	@Column(name="tags")
	private Set<String> tags = new HashSet<String>();
	
	@JsonSerialize
	@JsonProperty("metadata")
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyColumn(name="mapkey")
	@Column(name="mapvalue")
	@CollectionTable(name="service_metadata", joinColumns = {@JoinColumn(name="sid")})
	private Map<String,String> metadata = new HashMap<String,String>();
	
	@JsonSerialize
	@JsonProperty("requires")
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(
	        name="service_requires",
	        joinColumns=@JoinColumn(name="sid")
	  )
	@Column(name="requires")
	private Set<String> requires = new HashSet<String>();
	
	public ServiceDefinition(String id, String name, String description, boolean bindable, Set<Plan> plans) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.bindable = bindable;
		this.setPlans(plans);
	}

	public ServiceDefinition(String id, String name, String description, boolean bindable, Set<Plan> plans,
			Set<String> tags, Map<String,String> metadata, Set<String> requires) {
		this(id, name, description, bindable, plans);
		setTags(tags);
		setMetadata(metadata);
		setRequires(requires);
	}
	
	public ServiceDefinition(){}
	
	public ServiceDefinition(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isBindable() {
		return bindable;
	}

	public Set<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Set<Plan> plans) {
		if ( plans == null ) {
			// ensure serialization as an empty array and not null
			this.plans = new HashSet();
		} else {
			this.plans = plans;
		}
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		if (tags == null) {
			this.tags = new HashSet<String>();
		} else {
			this.tags = tags;
		}
	}

	public Set<String> getRequires() {
		return requires;
	}

	public void setRequires(Set<String> requires) {
		if (requires == null) {
			this.requires = new HashSet<String>();
		} else {
			this.requires = requires;
		}
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		if (metadata == null) {
			this.metadata = new HashMap<String,String>();
		} else {
			this.metadata = metadata;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}

}
