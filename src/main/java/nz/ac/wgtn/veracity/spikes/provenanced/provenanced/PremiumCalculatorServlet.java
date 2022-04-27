package nz.ac.wgtn.veracity.spikes.provenanced.provenanced;

import nz.ac.wgtn.veracity.spikes.provenanced.commons.PremiumCalculator;
import nz.ac.wgtn.veracity.spikes.provenanced.instrumentation.DynamicInstrumentation;
import nz.ac.wgtn.veracity.spikes.provenanced.instrumentation.ProvenanceCollector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Baseline premium calculator. Service, produced plain text (the amount in $$).
 * @author  Jens Dietrich
 */
public class PremiumCalculatorServlet extends HttpServlet {

    // instrument class -- could be done in a filter etc for all classes
    static {
        DynamicInstrumentation.transform(PremiumCalculatorServlet.class);
        DynamicInstrumentation.transform(PremiumCalculator.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        int premium = -1;
        String path = request.getPathInfo();
        if (path!=null) {
            try {
                String lastToken = path.substring(1+path.lastIndexOf('/'));
                int age = Integer.parseInt(lastToken);
                premium = PremiumCalculator.calculate(age);
                // get from execution
                List<String> provenance = ProvenanceCollector.getAndReset();
                // store for provenance requests
                int provenanceId = ProvenanceStore.store(provenance);
                request.getSession().setAttribute("provenance",provenance);
                String provenanceURL = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/provenance/" + provenanceId;
                response.setHeader("provenance",provenanceURL);
            }
            catch (Exception x) {} // no age path token, age not numeric, ..
        }
        if (premium==-1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            PrintWriter out = response.getWriter();
            out.println(premium);
            out.close();
        }
    }
}