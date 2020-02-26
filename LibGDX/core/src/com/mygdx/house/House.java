package com.mygdx.house;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.renderable.Constants;
import com.mygdx.renderable.NPC;

public class House {
	
	private int[][] background;
	private int[][] backgroundProperties;

	private List<Torch> torches;

	private HashMap<Integer, Texture> textures;
	public List<String> textureURL;
	private String houseFile;
	private int indicator = 0;
	private String houseProperties;
	private List<BodyDef> walls;
	
	public House(String[] attributes) {
		textureURL = new LinkedList<>();
		textures = new HashMap<>();
		torches = new ArrayList<>();
		walls = new ArrayList<>();
		setTextures(attributes);
		createLevel();
		createProperties();

	}

	public House(int[][] level, List<Torch> torches, HashMap<Integer, Texture> textures, List<String> textureURL) {
		this.background = level;
		this.torches = torches;
		this.textures = textures;
		this.textureURL = textureURL;
		walls = new ArrayList<>();
		createBodiesFromArray();
	}

	public int[][] getArray() {
		System.out.println(Arrays.deepToString(background));
		return background;
	}
	
	private void setTextures(String[] attributes) {
		for(String attribute : attributes) {
			if(attribute.contains(".csv")) {
				houseFile = "levels/"+attribute;
				houseProperties = "levels/"+attribute.substring(0, attribute.indexOf("."))+"properties.csv";
			}
		}
		for(String attribute : attributes) {
			if(attribute.contains(".gif") && !attribute.contains("house") && !attribute.contains("House")) {
				Texture t = new Texture(Gdx.files.internal("levels/" + attribute));
				textures.put(indicator, t);
				textureURL.add("levels/" + attribute);
				indicator++;
			}
		}
	}
	
	public Texture getTexture(int value) {
		return textures.get(value);
	}


	public void createBodiesFromArray() {
		for(int r=0; r<background.length; r++) {
			for (int c = background[r].length-1; c > -1; c--) {
				if (background[r][c] == 1) {

					createBodyDef(r, c);
				}
			}
		}
	}

	public void createBodies(World world) {
		for(BodyDef def : walls) {
            Body body = world.createBody(def);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(32f/2f, 32f/2f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f; 
            fixtureDef.filter.categoryBits = Constants.WALL;
                
            body.createFixture(fixtureDef);
		}
		
    }

	public void createBodyDef(int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set((y*32)+16, (x*32)+16);
        bodyDef.fixedRotation = true;
		walls.add(bodyDef);
	}

    private void createLevel() {
        FileHandle handle;
        try {
            handle = Gdx.files.internal(houseFile);
            String page  = handle.readString();
            String[] lines = page.split("\\r?\\n");
            background = new int[lines.length][(lines[0].length()+1)/2];
            //height = (lines[0].length()+1)/2;
            //width = lines.length;
            
            
            final String splitter = ",";
            //load reader
            //stores the array's height (height-1 to make 0,0 bottom left not top right)
            int arrayHeight = lines.length-1;
            //check if there is next line
            for(String line : lines) {
                //creates an array with every element in the current line
                String[] currentLine = line.split(splitter);
                //stores the current position in the array
                int arrayPosition = 0;
                //for every element in the current line
                for(String i:currentLine) {
                    //creates array
                    background[arrayHeight][arrayPosition] = Integer.parseInt(i);
                    if(Integer.parseInt(i) == 1) {
                    	createBodyDef(arrayHeight, arrayPosition);
                    }
                    //increment array position
                    arrayPosition++;
                }
                //increment array height location
                arrayHeight--;
            }
        } catch(GdxRuntimeException e) {
            e.printStackTrace();
        }
    }
    
    private void createProperties() {
        FileHandle handle;
        try {
            handle = Gdx.files.internal(houseProperties);
            String page  = handle.readString();
            String[] lines;
			lines = page.split("\\r?\\n");
			backgroundProperties = new int[lines.length][(lines[0].length()+1)/2];
            
            final String splitter = ",";
            //load reader
            //stores the array's height (height-1 to make 0,0 bottom left not top right)
            int arrayHeight = lines.length-1;
            //check if there is next line
            for(String line : lines) {
                //creates an array with every element in the current line
                String[] currentLine = line.split(splitter);
                //stores the current position in the array
                int arrayPosition = 0;
                //for every element in the current line
                for(String i:currentLine) {
                    //creates array
                    backgroundProperties[arrayHeight][arrayPosition] = Integer.parseInt(i);
                    generateTorches(arrayHeight, arrayPosition);
                    //increment array position
                    arrayPosition++;
                }
                //increment array height location
				arrayHeight -= 1;
            }
        } catch(GdxRuntimeException e) {
            e.printStackTrace();
        }
    }

	private void generateTorches(int x, int y) {
		/*                        
		 * Each tile is 32*32
		 * Hence we divide the coordinates by 32 and round down.
		 * This will be = to the tile the player is interacting with.
		 * If the tile is 1 in the array (a wall) return collision as true
		 */
		int value = backgroundProperties[x][y];
	    if(value > 0 && value < 5) {
		    int positionX = y*32;
		    int positionY = x*32;
		    
		    if(value == 1) {
		    	torches.add(new Torch(positionX, positionY, 0f));
		    }
		    
		    if(value == 2) {
		    	torches.add(new Torch(positionX, positionY, 90f));
		    }
		    
		    if(value == 3) {
		    	torches.add(new Torch(positionX, positionY, 180f));
		    }
		    
		    if(value == 4) {
		    	torches.add(new Torch(positionX, positionY, 270f));
		    }
	    }
	}
	
	public List<Torch> getTorches() {
		return torches;
	}

	public int[][] getLevel() {
		return background;
	}
	public Map<Integer, Texture> getTextures() { return textures; }
}
