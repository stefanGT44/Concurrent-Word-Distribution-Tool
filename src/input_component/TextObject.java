package input_component;

import output_component.ObjectType;

public class TextObject {
	
	private String name;
	private String data;
	private ObjectType objectType;
	
	public TextObject(String name, String data, ObjectType objectType) {
		this.name = name;
		this.data = data;
		this.objectType = objectType;
	}
	
	public ObjectType getObjectType() {
		return objectType;
	}
	
	public String getData() {
		return data;
	}
	
	public String getName() {
		return name;
	}

}
