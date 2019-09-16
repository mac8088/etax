package net.atos.bpm.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonpCallbackFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(JsonpCallbackFilter.class);

	public void init(FilterConfig fConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Map<String, String[]> parms = httpRequest.getParameterMap();

		// check whether exist the callback of JSONP
		if (parms.containsKey("jsonpCallback")) {
			if (log.isDebugEnabled())
				log.debug("Wrapping response with JSONP callback '" + parms.get("jsonpCallback")[0] + "'");

			OutputStream out = httpResponse.getOutputStream();

			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);

			chain.doFilter(request, wrapper);

			// handles the content-size truncation
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(new String(parms.get("jsonpCallback")[0] + "(").getBytes());
			outputStream.write(wrapper.getData());
			outputStream.write(new String(");").getBytes());
			byte jsonpResponse[] = outputStream.toByteArray();

			wrapper.setContentType("text/javascript;charset=UTF-8");
			wrapper.setContentLength(jsonpResponse.length);
			out.write(jsonpResponse);
			out.close();

		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {

	}

	class GenericResponseWrapper extends HttpServletResponseWrapper {

		private ByteArrayOutputStream output;
		private int contentLength;
		private String contentType;

		public GenericResponseWrapper(HttpServletResponse response) {
			super(response);
			output = new ByteArrayOutputStream();
		}

		public ServletOutputStream getOutputStream() {
			return new FilterServletOutputStream(output);
		}

		public PrintWriter getWriter() {
			return new PrintWriter(getOutputStream(), true);
		}

		public void setContentLength(int length) {
			this.contentLength = length;
			super.setContentLength(length);
		}

		public int getContentLength() {
			return contentLength;
		}

		public void setContentType(String type) {
			this.contentType = type;
			super.setContentType(type);
		}

		public String getContentType() {
			return contentType;
		}

		public byte[] getData() {
			return output.toByteArray();
		}
	}

	class FilterServletOutputStream extends ServletOutputStream {

		private DataOutputStream stream;
		private WriteListener writeListener;

		public FilterServletOutputStream(OutputStream output) {
			stream = new DataOutputStream(output);
		}

		public void write(int b) throws IOException {
			stream.write(b);
		}

		public void write(byte[] b) throws IOException {
			stream.write(b);
		}

		public void write(byte[] b, int off, int len) throws IOException {
			stream.write(b, off, len);
		}

		public void setWriteListener(WriteListener writeListener) {
			this.writeListener = writeListener;
		}

		public WriteListener getWriteListener() {
			return writeListener;
		}

		public boolean isReady() {
			return true;
		}
	}
}
