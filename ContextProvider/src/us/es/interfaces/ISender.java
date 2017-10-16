package us.es.interfaces;

import java.io.Serializable;
import java.util.Map;

public interface ISender {

	public abstract void write(Serializable message);

	public abstract void write(String id, Serializable message);

	public abstract void send();

	public abstract Map<String, String> receive();

}