package com.sdamsdam.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.util.Log;

//?�스???�일???�정 ?�보�???��?�는 ?�래?? ?�드로이?�의 ?�레?�런?��? ?�무 ?�려 ?�로 만듬
//Ready()�??�출?�여 ?�출??�?��?�고 기록???�는 CommitWrite, ?�기�??�을 ?�는 EndReady�??�출?�다.
class TextPref {
	String mPath;
	StringBuilder mBuf;
	static final String HEADER = "__Text Preference File__\n";
	private static final String TAG = "TextPref_TAG"; 

	// ?�성?�로 ?�레?�런?�의 ?�전 경로�??�달?�다. 
	public TextPref(String Path) throws Exception {
		mPath = Path;
		File file = new File(mPath);
		if (file.exists() == false) {
			Log.d(TAG, "if  ");
			FileOutputStream fos = new FileOutputStream(file);
			Log.d(TAG, "new FileOutputStream(  ");
			fos.write(HEADER.getBytes());
			fos.close();
		}
	}

	// ?�정 ?�일????��?�다.
	public void Reset() {
		File file = new File(mPath);
		file.delete();
	}

	// 버퍼�?�?��?�여 ?�기 �??�기 �?���??�다.
	public boolean Ready() {
		try {
			
			Log.d(TAG, "try {: ");
			FileInputStream fis = new FileInputStream(mPath);
			Log.d(TAG, "new FileInputStream(mPath) : ");
			int avail = fis.available();
			byte[] data = new byte[avail];
			Log.d(TAG, "fis.available(); : ");
			
			
			while (fis.read(data) != -1) {;}
			fis.close();
			mBuf = new StringBuilder(avail);
			mBuf.append(new String(data));
			Log.d(TAG, "while (fis.read(data) != -1) {;} : ");
			
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	// 버퍼???�용???�일�?기록?�다.
	public boolean CommitWrite() {
		File file = new File(mPath);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mBuf.toString().getBytes());
			fos.close();
		} 
		catch (Exception e) {
			return false;
		}
		mBuf = null;
		return true;
	}

	// 버퍼�??�제?�고 ?�기�?종료?�다. �?��???�용??모두 취소?�다.
	public void EndReady() {
		mBuf = null;
	}
	
	// name?�의 ?�치�?�?��?�여 = ?�음 ?�치�?리턴?�다. ?�으�?-1??리턴?�다.
	// ?�연??중복 방�?�??�해 ???�름?�에 __�?붙인??
	int FindIdx(String name) {
		String key = "__" + name + "=";
		int idx = mBuf.indexOf(key);
		if (idx == -1) {
			return -1;
		} else {
			return idx + key.length();
		}
	}

	// 문자???��? 기록?�다. ?��? ?�으�???��?�다.
	public void WriteString(String name, String value) {
		int idx = FindIdx(name);
		if (idx == -1) {
			mBuf.append("__");
			mBuf.append(name);
			mBuf.append("=");
			mBuf.append(value);
			mBuf.append("\n");
		} else {
			int end = mBuf.indexOf("\n", idx);
			mBuf.delete(idx, end);
			mBuf.insert(idx, value);
		}
	}

	// 문자???��? ?�는?? ?�으�??�폴?��? 리턴?�다.
	public String ReadString(String name, String def) {
		int idx = FindIdx(name);
		if (idx == -1) {
			return def;
		} else {
			int end = mBuf.indexOf("\n", idx);
			return mBuf.substring(idx, end);
		}
	}

	// ?�수�??�는?? ?�단 문자???�태�??��? ??�?��?�다.
	public void WriteInt(String name, int value) {
		WriteString(name, Integer.toString(value));
	}

	// ?�수�?기록?�다. 문자???�태�?�?��?�여 기록?�다.
	public int ReadInt(String name, int def) {
		String s = ReadString(name, "__none");
		if (s.equals("__none")) {
			return def;
		}
		try {
			return Integer.parseInt(s);
		}
		catch (Exception e) {
			return def;
		}
	}
	
	public void WriteLong(String name, long value) {
		WriteString(name, Long.toString(value));
	}
	
	public long ReadLong(String name, long def) {
		String s = ReadString(name, "__none");
		if (s.equals("__none")) {
			return def;
		}
		try {
			return Long.parseLong(s);
		}
		catch (Exception e) {
			return def;
		}
	}
	
	// 진위값�? true, false�??�닌 1, 0?�로 기록?�다.
	public void WriteBoolean(String name, boolean value) {
		WriteString(name, value ? "1":"0");
	}

	public boolean ReadBoolean(String name, boolean def) {
		String s = ReadString(name, "__none");
		if (s.equals("__none")) {
			return def;
		}
		try {
			return s.equals("1") ? true:false;
		}
		catch (Exception e) {
			return def;
		}
	}
	
	public void WriteFloat(String name, float value) {
		WriteString(name, Float.toString(value));
	}

	public float ReadFloat(String name, float def) {
		String s = ReadString(name, "__none");
		if (s.equals("__none")) {
			return def;
		}
		try {
			return Float.parseFloat(s);
		}
		catch (Exception e) {
			return def;
		}
	}
	
	// ?�꺼번에 값을 ?�입?�기 ?�해 �?��?�다. ?�더 ?�성?�고 충분??버퍼�??�당?�다.
	void BulkWriteReady(int length) {
		mBuf = new StringBuilder(length);
		mBuf.append(HEADER);
		mBuf.append("\n");
	}

	// 문자???�태�?받�? 값을 무조�??�에 ?�붙?�다.
	void BulkWrite(String name, String value) {
		mBuf.append("__");
		mBuf.append(name);
		mBuf.append("=");
		mBuf.append(value);
		mBuf.append("\n");
	}

	// ?��? ??��?�다. 
	void DeleteKey(String name) {
		int idx = FindIdx(name);
		if (idx != -1) {
			int end = mBuf.indexOf("\n", idx);
			mBuf.delete(idx - (name.length() + 3), end + 1);
		}
	}
}
