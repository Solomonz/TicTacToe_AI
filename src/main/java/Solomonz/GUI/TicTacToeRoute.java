package Solomonz.GUI;


import com.google.gson.Gson;

import Solomonz.main.Main;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class TicTacToeRoute implements Route
{
    private static final Gson GSON = new Gson();
    private Main m;

    public TicTacToeRoute(Main m)
    {
        this.m = m;
    }

    @Override
    public Object handle(Request req, Response res)
    {
        QueryParamsMap qm = req.queryMap();
        if (qm.value("type").equals("move"))
        {
            int row = Integer.parseInt(qm.value("row"));
            int col = Integer.parseInt(qm.value("col"));
            System.out.println("moving: " + row + " " + col);

            return GSON.toJson(m.getGame().userPlayed(row, col));
        }
        else
        {
            assert (qm.value("type").equals("reset"));
            System.out.println("resetting");
            return GSON.toJson(m.getGame().newGame());
        }
    }

}
