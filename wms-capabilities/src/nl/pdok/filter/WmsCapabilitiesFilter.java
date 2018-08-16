package nl.pdok.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class WmsCapabilitiesFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        System.out.println("Ok in Filter!");
//        RequestDispatcher rdObj = null;
//        PrintWriter out = resp.getWriter();
//        out.write("<html><body><p>HELLO<p></body></html>");
//        out.close();


        if (!(request instanceof HttpServletRequest)) {
            // We only do HTTP
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String method = httpServletRequest.getMethod();
        if (method.equalsIgnoreCase("get")) {
            // The request is a parameter, possibly in different capitalizations
            String owsRequestName = request.getParameter("request");
            if (owsRequestName == null) {
                owsRequestName = request.getParameter("REQUEST");
            }
            if (owsRequestName == null) {
                owsRequestName = request.getParameter("Request");
            }
            String owsServiceName = request.getParameter("service");
            if (owsServiceName == null) {
                owsServiceName = request.getParameter("SERVICE");
            }
            if (owsServiceName == null) {
                owsServiceName = request.getParameter("Service");
            }

            if (owsRequestName != null && owsServiceName != null &&
                    owsServiceName.equalsIgnoreCase("wms") && owsRequestName.equalsIgnoreCase("getcapabilities")) {
                // System.out.println("WMS GetCapabilities");

                try {
                    response.setContentType("application/xml; charset=utf-8");
                    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wms-capabilities.xml");
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder cb = new StringBuilder();
                    try {

                        String sCurrentLine;
                        while ((sCurrentLine = in.readLine()) != null) {
                            cb.append(sCurrentLine).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Put in end date for each Layer with Dimension as current date/time
                    // in the form 2018-08-16T09:00:00.000Z
                    // Quoted "Z" to indicate UTC, no timezone offset
                    TimeZone tz = TimeZone.getTimeZone("UTC");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH':00:00.000Z'");
                    df.setTimeZone(tz);
                    String nowAsISO = df.format(new Date());
                    System.out.println("nowAsISO=" + nowAsISO);
                    String replaceStr = "{DIM_END_TIME}";
                    // System.out.println("========== cb=" + cb.toString());

                    String outString = cb.toString().replace(replaceStr, nowAsISO);
                    PrintWriter out = response.getWriter();
                    // System.out.println("========== outString=" + outString);
                    out.write(outString);
                    out.flush();
                    out.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        // Other HTTP methods or not WMS GetCapabilities
        // System.out.println("Other OWS request");
        chain.doFilter(request, response);
        return;


//        // Figure out the domain name and the visible features for that domain
//        String domainName = null;
//        String visibleFeatures = null;
//        try {
//            URI uri = new URI(httpServletRequest.getRequestURL().toString());
//            domainName = uri.getHost();
//            visibleFeatures = featureAccessProps.getProperty(domainName);
//        } catch (Throwable t) {
//            // ignore
//            p("error getting visibleFeatures from domain name " + t);
//        }
//
//        // Ignore if no visible features
//        if (visibleFeatures == null || visibleFeatures.equals("*") || owsRequestName == null || !FILTERED_OWS_METHODS.contains(owsRequestName.toLowerCase())) {
//            chain.doFilter(httpServletRequest, response);
//            return;
//        }
//
//        String url = ((HttpServletRequest) request).getRequestURL().toString();
//        p("doing " + owsRequestName + " URL=" + url + " d=" + domainName + " f=" + visibleFeatures);
//
//        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
//
//        // Delegate to next Filter in chain
//        chain.doFilter(httpServletRequest, responseWrapper);
//
//        // Deal with caching (?)
//        byte[] origXML = responseWrapper.getBuffer();
//        if (origXML == null || origXML.length == 0) {
//            // just let Tomcat deliver its cached data back to the client
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Get response from servlet
//        ByteArrayInputStream origXMLIn = new ByteArrayInputStream(origXML);
//        Source xmlSource = new StreamSource(origXMLIn);
//
//        // Transform response and send to client
//        PrintWriter out = response.getWriter();
//        try {
//            CharArrayWriter caw = new CharArrayWriter();
//            StreamResult result = new StreamResult(caw);
//
//            // Need concurrent lock since transformer is non thread-safe
//            // this could be optimized but this is not called very often
//            synchronized (transformer) {
//
//                transformer.setParameter("features", visibleFeatures);
//
//                transformer.transform(xmlSource, result);
//            }
//            p("transform ok");
//            response.setContentLength(caw.toString().length());
//            out.write(caw.toString());
//            p("out.write ok");
//        } catch (Throwable ex) {
//            out.println(ex.toString());
//            out.write(responseWrapper.toString());
//            p("out.write error" + ex);
//            ex.printStackTrace();
//        }
//
    }

//    private static class BufferedHttpResponseWrapper extends HttpServletResponseWrapper {
//        private BufferedServletOutputStream bufferedServletOut
//                = new BufferedServletOutputStream();
//
//        private PrintWriter printWriter = null;
//        private ServletOutputStream outputStream = null;
//
//        public BufferedHttpResponseWrapper(HttpServletResponse origResponse) {
//            super(origResponse);
//        }
//
//        public byte[] getBuffer() {
//            return this.bufferedServletOut.getBuffer();
//        }
//
//        public PrintWriter getWriter() throws IOException {
//            if (this.outputStream != null) {
//                throw new IllegalStateException(
//                        "The Servlet API forbids calling getWriter( ) after"
//                                + " getOutputStream( ) has been called");
//            }
//
//            if (this.printWriter == null) {
//                this.printWriter = new PrintWriter(this.bufferedServletOut);
//            }
//            return this.printWriter;
//        }
//
//        public ServletOutputStream getOutputStream() throws IOException {
//            if (this.printWriter != null) {
//                throw new IllegalStateException(
//                        "The Servlet API forbids calling getOutputStream( ) after"
//                                + " getWriter( ) has been called");
//            }
//
//            if (this.outputStream == null) {
//                this.outputStream = this.bufferedServletOut;
//            }
//            return this.outputStream;
//        }
//
//        // override methods that deal with the response buffer
//
//        public void flushBuffer() throws IOException {
//            if (this.outputStream != null) {
//                this.outputStream.flush();
//            } else if (this.printWriter != null) {
//                this.printWriter.flush();
//            }
//        }
//
//        public int getBufferSize() {
//            return this.bufferedServletOut.getBuffer().length;
//        }
//
//        public void reset() {
//            this.bufferedServletOut.reset();
//        }
//
//        public void resetBuffer() {
//            this.bufferedServletOut.reset();
//        }
//
//        public void setBufferSize(int size) {
//            this.bufferedServletOut.setBufferSize(size);
//        }
//    }
//
//    /**
//     * A custom servlet output stream that stores its data in a buffer,
//     * rather than sending it directly to the client.
//     *
//     * @author Eric M. Burke
//     */
//    private static class BufferedServletOutputStream extends ServletOutputStream {
//        // the actual buffer
//        private ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        /**
//         * @return the contents of the buffer.
//         */
//        public byte[] getBuffer() {
//            return this.bos.toByteArray();
//        }
//
//        /**
//         * This method must be defined for custom servlet output streams.
//         */
//        public void write(int data) {
//            this.bos.write(data);
//        }
//
//        // BufferedHttpResponseWrapper calls this method
//        public void reset() {
//            this.bos.reset();
//        }
//
//        // BufferedHttpResponseWrapper calls this method
//        public void setBufferSize(int size) {
//            // no way to resize an existing ByteArrayOutputStream
//            this.bos = new ByteArrayOutputStream(size);
//        }
//    }
//
//    public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
//
//        private byte[] body;
//
//        private AtomicBoolean bodyRead = new AtomicBoolean(false);
//
//        public MultiReadHttpServletRequest(HttpServletRequest httpServletRequest) {
//            super(httpServletRequest);
//        }
//
//        @Override
//        public ServletInputStream getInputStream() throws IOException {
//            if (!bodyRead.getAndSet(true)) {
//                // Read the request body and save it as a byte array
//                InputStream is = super.getInputStream();
//                body = toByteArray(is);
//            }
//            return new ServletInputStreamImpl(new ByteArrayInputStream(body));
//        }
//
//        @Override
//        public BufferedReader getReader() throws IOException {
//            String enc = getCharacterEncoding();
//            if (enc == null) enc = "UTF-8";
//            return new BufferedReader(new InputStreamReader(getInputStream(), enc));
//        }
//
//        private byte[] toByteArray(InputStream is) throws IOException {
//            byte[] buffer = new byte[1025];
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            int n;
//            while (-1 != (n = is.read(buffer))) {
//                output.write(buffer, 0, n);
//            }
//            return output.toByteArray();
//        }
//
//        private class ServletInputStreamImpl extends ServletInputStream {
//
//            private InputStream is;
//
//            public ServletInputStreamImpl(InputStream is) {
//                this.is = is;
//            }
//
//            public int read() throws IOException {
//                return is.read();
//            }
//
//            public boolean markSupported() {
//                return false;
//            }
//
//            public synchronized void mark(int i) {
//                throw new RuntimeException(new IOException("mark/reset not supported"));
//            }
//
//            public synchronized void reset() throws IOException {
//                throw new IOException("mark/reset not supported");
//            }
//        }
//
//    }
}
