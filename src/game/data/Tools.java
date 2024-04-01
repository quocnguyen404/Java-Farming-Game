package game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.RenderHandler;
import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstanst;
import game.Helper;

public class Tools 
{
    public enum ToolID
    {
        SHOVEL,
        HAMMER,
        SICKLE,
    }

    private ArrayList<Tool> toolsList;

    public Tools()
    {
        File toolsFile = new File(GameConstanst.TOOLS_PATH);
        toolsList = new ArrayList<>();
        SpriteSheet sheet = new SpriteSheet(Helper.loadImage(GameConstanst.TOOLS_SHEET_PATH));
        sheet.loadSprite(GameConstanst.TILE_WIDTH, GameConstanst.TILE_HEIGHT);

        try 
        {
            Scanner scanner = new Scanner(toolsFile);
            int id = 0;
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!line.startsWith("//"))
                {
                    String[] splitStrings = line.split("-");
                    String toolName = splitStrings[0];
                    int x = Integer.parseInt(splitStrings[1]);
                    int y = Integer.parseInt(splitStrings[2]);
                    Tool newTool = new Tool(sheet.getSprite(x, y), toolName, ToolID.values()[id]);
                    toolsList.add(newTool);
                    id++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }


    public void renderTool(ToolID toolID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom)
    {
        if (toolsList.size() > toolID.ordinal())
            renderer.renderSprite(toolsList.get(toolID.ordinal()).sprite, xPosition, yPosition, xZoom, yZoom, true);
    }


    public int getSize()
    {
        return toolsList.size();
    }

    public Tool getTool(ToolID toolID)
    {
        return toolsList.get(toolID.ordinal());
    }

    public ArrayList<Tool> getToolList()
    {
        return toolsList;
    }

    public class Tool 
    {
        public ToolID toolID;
        public String toolName;
        public Sprite sprite;
        
        public Tool (Sprite sprite, String toolName, ToolID toolID)
        {
            this.sprite = sprite;
            this.toolName = toolName;
            this.toolID = toolID;
        }
    }
}
