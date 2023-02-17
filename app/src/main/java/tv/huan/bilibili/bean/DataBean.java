package tv.huan.bilibili.bean;

import java.io.Serializable;


public class DataBean<T> implements Serializable{
	public static final String TAG = DataBean.class.getSimpleName();
	private static final long serialVersionUID = -4977701147580473625L;

	private T data;
	private int code ;
	private String status ;

	@Override
	public String toString() {
		return "DataBean{" +
				"data=" + data +
				", code=" + code +
				", status='" + status + '\'' +
				'}';
	}

	public static String getTAG() {
		return TAG;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStutas() {
		return status;
	}

	public void setStutas(String stutas) {
		this.status = stutas;
	}
}
