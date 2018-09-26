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

                // Caps request: determine version
                String owsVersion = request.getParameter("version");
                if (owsVersion == null) {
                    owsVersion = request.getParameter("VERSION");
                }
                if (owsVersion == null) {
                    owsVersion = request.getParameter("Version");
                }
                if (owsVersion == null) {
                    owsVersion = "1.3.0";
                }

                // Unless WMS version 1 (1.0.0, 1.1.0 or 1.1.1) is explicitly requested we assume v2 (1.3.0)
                String capsTemplateFile = "wms-capabilities-v2.xml";
                if (owsVersion.startsWith("1.0") || owsVersion.startsWith("1.1")) {
                    capsTemplateFile = "wms-capabilities-v1.xml";
                }

                try {
                    response.setContentType("application/xml; charset=utf-8");
                    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(capsTemplateFile);
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
                    String replaceStr = "{DIM_END_TIME}";

                    String outString = cb.toString().replace(replaceStr, nowAsISO);
                    PrintWriter out = response.getWriter();
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
    }
}
