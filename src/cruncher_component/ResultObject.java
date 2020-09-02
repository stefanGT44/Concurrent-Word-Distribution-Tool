package cruncher_component;

import java.util.Map;
import java.util.concurrent.Future;

import output_component.ObjectType;

public class ResultObject {
	
	private String fileName;
	private	Future<Map<String, Integer>> data;
	private ObjectType objectType;
	
	public ResultObject(String fileName, Future<Map<String, Integer>> data, ObjectType objectType) {
		super();
		this.fileName = fileName;
		this.data = data;
		this.objectType = objectType;
	}
	
	public ObjectType getObjectType() {
		return objectType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public Future<Map<String, Integer>> getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
