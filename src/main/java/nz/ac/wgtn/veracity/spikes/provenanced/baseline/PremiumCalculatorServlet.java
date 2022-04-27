package nz.ac.wgtn.veracity.spikes.provenanced.baseline;

import nz.ac.wgtn.veracity.spikes.provenanced.commons.*;
import java.io.*;
import javax.servlet.http.*;

/**
 * Baseline premium calculator. Service, produced plain text (the amount in $$).
 * @author  Jens Dietrich
 */
public class PremiumCalculatorServlet extends HttpServlet {

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
            }
            catch (Exception x) {} // no age parameter, age not numeric, ..
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