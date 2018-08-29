package com.ticxo.modelapi.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.bukkit.util.Vector;

import com.ticxo.modelapi.Main;

import us.fihgu.toolbox.resourcepack.model.DisplayEntry;
import us.fihgu.toolbox.resourcepack.model.DisplayOptions;
import us.fihgu.toolbox.resourcepack.model.ElementFaceOptions;
import us.fihgu.toolbox.resourcepack.model.ModelElement;

public class JsonModel {

	private ModelRenderer model;
	private Vector from;
	private Vector to;
	private Double scale;
	private Integer offsetX;
	private Integer offsetY;
	private Map<String, TextureUV> uv = new HashMap<String, TextureUV>();

	public JsonModel(ModelRenderer model) {

		this.model = model;
		this.from = new Vector(8 + model.getBox().getOffset().getX(),
				8 - (model.getBox().getSize().getY() + model.getBox().getOffset().getY()),
				8 + model.getBox().getOffset().getZ());
		this.to = new Vector(from.getX() + model.getBox().getSize().getX(),
				from.getY() + model.getBox().getSize().getY(), from.getZ() + model.getBox().getSize().getZ());

		generateTextureUVs();
		
	}

	public Vector getFrom() {
		
		return from;
		
	}
	
	public Vector getTo() {
		
		return to;
		
	}
	
	public List<ModelElement> getElements() {
		
		List<ModelElement> element = new ArrayList<ModelElement>();
		ModelElement me = new ModelElement();
		me.setFaces(getUV());
		me.setFrom(new double[] {from.getX(), from.getY(), from.getZ()});
		me.setTo(new double[] {to.getX(), to.getY(), to.getZ()});
		element.add(me);
		
		return element;
		
	}
	
	public ElementFaceOptions getUV() {
		
		ElementFaceOptions faces = new ElementFaceOptions();
		
		faces.setNorth(uv.get("north").getElementFaceEntry());
		faces.setEast(uv.get("east").getElementFaceEntry());
		faces.setSouth(uv.get("south").getElementFaceEntry());
		faces.setWest(uv.get("west").getElementFaceEntry());
		faces.setUp(uv.get("up").getElementFaceEntry());
		faces.setDown(uv.get("down").getElementFaceEntry());
		
		return faces;
		
	}

	public DisplayOptions getDisplay() {

		DisplayOptions option = new DisplayOptions();
		DisplayEntry head = new DisplayEntry();
		
		head.setScale(new double[] {1.6, 1.6, 1.6});
		head.setTranslation(new double[] {0, -6.5, 0});
		
		option.setHead(head);
		
		return null;
	}

	public void generateTextureUVs() {
		
		this.scale = model.getModel().textureSize / 16d;
		this.offsetX = model.getTextureOffset("X");
		this.offsetY = model.getTextureOffset("Y");
		this.uv.put("north",
				new TextureUV(offsetX + model.getBox().getSize().getZ(), offsetY + model.getBox().getSize().getZ(),
						model.getBox().getSize().getX(), model.getBox().getSize().getY(), scale, model.isMirror()));
		this.uv.put("east", new TextureUV(offsetX * 1d, offsetY + model.getBox().getSize().getZ(),
				model.getBox().getSize().getZ(), model.getBox().getSize().getY(), scale, model.isMirror()));
		this.uv.put("south",
				new TextureUV(offsetX + model.getBox().getSize().getZ() * 2 + model.getBox().getSize().getX(),
						offsetY + model.getBox().getSize().getZ(), model.getBox().getSize().getX(),
						model.getBox().getSize().getY(), scale, model.isMirror()));
		this.uv.put("west",
				new TextureUV(offsetX + model.getBox().getSize().getZ() + model.getBox().getSize().getX(),
						offsetY + model.getBox().getSize().getZ(), model.getBox().getSize().getZ(),
						model.getBox().getSize().getY(), scale, model.isMirror()));
		this.uv.put("up", new TextureUV(offsetX + model.getBox().getSize().getZ(), offsetY * 1d,
				model.getBox().getSize().getX(), model.getBox().getSize().getZ(), scale, model.isMirror()));
		this.uv.put("down",
				new TextureUV(offsetX + model.getBox().getSize().getZ() + model.getBox().getSize().getX(), offsetY * 1d,
						model.getBox().getSize().getX(), model.getBox().getSize().getZ(), scale, model.isMirror()));
		
	}
	
	public void exportJson() {

		File json = new File(
				Main.plugin.getDataFolder() + "/TexturePack/assets/minecraft/models/entity/" + model.getModel().modelId,
				model.getPartId() + ".json");
		File texture = new File(Main.plugin.getDataFolder() + "/TexturePack/assets/minecraft/textures/entity",
				model.getModel().modelId + ".png");

		if (!json.exists()) {
			json.getParentFile().mkdirs();
			try {
				json.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!texture.exists()) {
			texture.getParentFile().mkdirs();
			try {
				OutputStream tex = new FileOutputStream(texture);
				IOUtils.copy(Main.plugin.getResource("resources/" + model.getModel().modelId + ".png"), tex);
				tex.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileWriter editor = new FileWriter(json);
			editor.write("{\r\n" + "	\"textures\": {\r\n" + "		\"0\": \"entity/" + model.getModel().modelId
					+ "\"\r\n" + "	},\r\n" + "	\"elements\": [\r\n" + "		{\r\n" + "			\"from\": ["
					+ from.getX() + ", " + from.getY() + ", " + from.getZ() + "],\r\n" + "			\"to\": ["
					+ to.getX() + ", " + to.getY() + ", " + to.getZ() + "],\r\n"
					+ "			\"rotation\": {\"angle\": 0, \"axis\": \"y\", \"origin\": [8, 8, 8]},\r\n"
					+ "			\"faces\": {\r\n" + "				\"north\": {\"uv\": [" + uv.get("north").getX()
					+ ", " + uv.get("north").getY() + ", " + uv.get("north").getoX() + ", " + uv.get("north").getoY()
					+ "], \"texture\": \"#0\"},\r\n" + "				\"east\": {\"uv\": [" + uv.get("east").getX()
					+ ", " + uv.get("east").getY() + ", " + uv.get("east").getoX() + ", " + uv.get("east").getoY()
					+ "], \"texture\": \"#0\"},\r\n" + "				\"south\": {\"uv\": [" + uv.get("south").getX()
					+ ", " + uv.get("south").getY() + ", " + uv.get("south").getoX() + ", " + uv.get("south").getoY()
					+ "], \"texture\": \"#0\"},\r\n" + "				\"west\": {\"uv\": [" + uv.get("west").getX()
					+ ", " + uv.get("west").getY() + ", " + uv.get("west").getoX() + ", " + uv.get("west").getoY()
					+ "], \"texture\": \"#0\"},\r\n" + "				\"up\": {\"uv\": [" + uv.get("up").getX() + ", "
					+ uv.get("up").getY() + ", " + uv.get("up").getoX() + ", " + uv.get("up").getoY()
					+ "], \"texture\": \"#0\", \"rotation\": 180},\r\n" + "				\"down\": {\"uv\": ["
					+ uv.get("down").getX() + ", " + uv.get("down").getY() + ", " + uv.get("down").getoX() + ", "
					+ uv.get("down").getoY() + "], \"texture\": \"#0\", \"rotation\": 180}\r\n" + "			}\r\n"
					+ "		}\r\n" + "	],\r\n" + "	\"display\": {\r\n" + "		\"head\": {\r\n"
					+ "			\"scale\": [1.6, 1.6, 1.6],\r\n" + "			\"translation\": [0, -6.5, 0]\r\n"
					+ "		}\r\n" + "	}\r\n" + "}");
			editor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ModelRenderer getModel() {

		return model;

	}

}
