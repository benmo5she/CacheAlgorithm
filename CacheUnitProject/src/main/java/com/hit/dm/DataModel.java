package com.hit.dm;

public class DataModel<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
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

	public DataModel(Long id, T content) {
		dataModelId = id;
		this.content = content;
	}
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
	@Override
	public String toString()
	{
		return super.toString();
	}
}
