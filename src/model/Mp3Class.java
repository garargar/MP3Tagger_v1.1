package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mp3Class {
	private StringProperty mp3Name;	
	private StringProperty mp3HasTag;
	private File mp3File;
	private Mp3File mp3 = null;
	
	private String mp3Loc;

	public Mp3Class(File mp3) {
		mp3Name = new SimpleStringProperty(mp3.getName());            
		mp3File = mp3;
		mp3Loc = mp3.getAbsolutePath();

		try {
			this.mp3 = new Mp3File(mp3File);
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(hasTag())
			mp3HasTag = new SimpleStringProperty("\u2713");
		else
			mp3HasTag = new SimpleStringProperty("\u2717");	
	}

	public File getMp3File() {
		return mp3File;
	}
	
	public String getMp3Loc(){
		return mp3Loc;
	}

	public StringProperty mp3NameProperty() {
		return mp3Name;
	}

	public StringProperty mp3HasTagProperty(){
		return mp3HasTag;
	}

	public boolean hasTag() {		
		if (mp3 != null && (mp3.hasId3v1Tag() || mp3.hasId3v2Tag() || mp3.hasCustomTag()))
			return true;
		else return false;
	}

	public void removeTag() {

		if (hasTag()){
			if (mp3.hasId3v1Tag()) {
				mp3.removeId3v1Tag();
			}
			if (mp3.hasId3v2Tag()) {
				mp3.removeId3v2Tag();
			}
			if (mp3.hasCustomTag()) {
				mp3.removeCustomTag();
			}
			
			try {
				mp3.save(mp3Name.get());
			} catch (NotSupportedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


			mp3File.delete();
			moveTo(mp3Name.get(), mp3Loc).delete();			
		}		
	}
	
	public File moveTo(String from, String to){
		InputStream inStream = null;
		OutputStream outStream = null;

		try{			 
			File afile =new File(from);
			File bfile =new File(to);
			
			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = inStream.read(buffer)) > 0){ 
				outStream.write(buffer, 0, length); 
			} 
			inStream.close();
			outStream.close();
			
			return afile;

		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}


}
