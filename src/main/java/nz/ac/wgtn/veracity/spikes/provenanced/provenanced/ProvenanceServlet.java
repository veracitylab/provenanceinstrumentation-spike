package nz.ac.wgtn.veracity.spikes.provenanced.provenanced;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Get the provenance information associated with a previous request.
 * @author  Jens Dietrich
 */
public class ProvenanceServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        int id = -1;
        String path = request.getPathInfo();
        if (path!=null) {
            try {
                String lastToken = path.substring(1+path.lastIndexOf('/'));
                id = Integer.parseInt(lastToken);
            }
            catch (Exception x) {} // no id path token, id not numeric, ..
        }
        if (id==-1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            PrintWriter out = response.getWriter();
            List<String> provenanceData = ProvenanceStore.get(id);
            out.println("# PROVENANCE");
            for (String method:provenanceData) {
                out.println(method);
            }
            out.close();
        }
    }
}