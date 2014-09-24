package tool.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessControlThread{
	public ProcessControlThread(Process p) {
		new StreamFlush(p.getInputStream()).start();
		new StreamFlush(p.getErrorStream()).start();
	}
	class StreamFlush extends Thread{
		InputStream is;
		public StreamFlush(InputStream is){
			this.is = is;
		}
		@Override
		public void run(){
			try {
				printStream(is);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		private void printStream(InputStream stream) throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
		}
	}

}
