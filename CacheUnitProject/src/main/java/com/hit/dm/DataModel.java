package com.hit.dm;

public class DataModel<T> implements java.io.Serializable {
	private Long dataModelId;
	private T content;

	public Long getDataModelId() {
		return dataModelId;
	}

	public void setDataModelId(Long dataModelId) {
		this.dataModelId = dataModelId;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	DataModel(Long id, T content) {
		dataModelId = id;
		this.content = content;
	}

	public int hashCode();

	public boolean equals(Object obj);

	public String toString();
}
