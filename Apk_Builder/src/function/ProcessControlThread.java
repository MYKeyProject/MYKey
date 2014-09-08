package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
class ProcessControlThread extends Thread {
	private Process p;
	private InputStream is;
	private InputStream eis;
	private OutputStream os;
	private StringBuffer msg;

	public ProcessControlThread(Process p) {
		this.p = p;
		is = p.getInputStream();
		eis = p.getErrorStream();
		os = p.getOutputStream();
	}

	//
	// public void run()
	// {
	// try
	// {
	// PrintWriter pw = null;
	// if (os != null)
	// pw = new PrintWriter(os);
	//
	// InputStreamReader isr = new InputStreamReader(is);
	// BufferedReader br = new BufferedReader(isr);
	// String line=null;
	// while ( (line = br.readLine()) != null)
	// {
	// if (pw != null)
	// pw.println(line);
	// System.out.println(line);
	// }
	// if (pw != null)
	// pw.flush();
	// } catch (IOException ioe)
	// {
	// ioe.printStackTrace();
	// }
	// }
	//
	@Override
	public void run() {
		try {
			printStream(is);
			printStream(eis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
