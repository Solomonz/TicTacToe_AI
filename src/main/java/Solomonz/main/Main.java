package Solomonz.main;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Main
{
    private static final int DEFAULT_PORT = 4567;

    public Main(int portNum)
    {
        run(portNum);
    }

    public static void main(String[] args)
    {
        OptionParser parser = new OptionParser();
        parser.accepts("gui");
        parser.accepts("port").withRequiredArg().ofType(Integer.class)
                .defaultsTo(DEFAULT_PORT);
        OptionSet options = parser.parse(args);

        int port = DEFAULT_PORT;

        try
        {
            if (options.has("gui"))
            {
                port = (int) options.valueOf("port");
                System.out.println(
                        "gui flag received, running server on localhost on port "
                                + port);
            }
        } catch (ClassCastException e)
        {
            System.out.println(
                    "ERROR: must supply a valid integer for the port argument");
            return;
        } catch (OptionException e)
        {
            System.out.println("ERROR: bad command");
            return;
        }

        new Main(port);
    }

    private void run(int port)
    {
        runSparkServer(port);
    }

    private void runSparkServer(int port)
    {
        Spark.port(port);
        Spark.externalStaticFileLocation("src/main/resources/static");
        Spark.exception(Exception.class, new ExceptionPrinter());

        Spark.get("/game", new TicTacToeFrontEnd(), createEngine());
        Spark.post("/game/AI", new TicTacToeAiFrontEnd());
    }

    private class TicTacToeFrontEnd implements TemplateViewRoute
    {

        @Override
        public ModelAndView handle(Request req, Response res)
        {
            Map<String, Object> variables = ImmutableMap.of("title",
                    "Tic Tac Toe");
            return new ModelAndView(variables, "tictactoe.ftl");
        }

    }

    private static FreeMarkerEngine createEngine()
    {
        Configuration config = new Configuration();
        File templates = new File(
                "src/main/resources/spark/template/freemarker");
        try
        {
            config.setDirectoryForTemplateLoading(templates);
        } catch (IOException ioe)
        {
            System.out.printf("ERROR: Unable use %s for template loading.%n",
                    templates);
            System.exit(1);
        }
        return new FreeMarkerEngine(config);
    }

    private static class ExceptionPrinter implements ExceptionHandler
    {
        @Override
        public void handle(Exception e, Request req, Response res)
        {
            res.status(500);
            StringWriter stacktrace = new StringWriter();
            try (PrintWriter pw = new PrintWriter(stacktrace))
            {
                pw.println("<pre>");
                e.printStackTrace(pw);
                pw.println("</pre>");
            }
            res.body(stacktrace.toString());
        }
    }
}
