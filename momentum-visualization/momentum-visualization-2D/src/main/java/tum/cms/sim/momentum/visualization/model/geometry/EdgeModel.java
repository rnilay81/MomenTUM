/*******************************************************************************
 * Welcome to the pedestrian simulation framework MomenTUM. 
 * This file belongs to the MomenTUM version 2.0.2.
 * 
 * This software was developed under the lead of Dr. Peter M. Kielar at the
 * Chair of Computational Modeling and Simulation at the Technical University Munich.
 * 
 * All rights reserved. Copyright (C) 2017.
 * 
 * Contact: peter.kielar@tum.de, https://www.cms.bgu.tum.de/en/
 * 
 * Permission is hereby granted, free of charge, to use and/or copy this software
 * for non-commercial research and education purposes if the authors of this
 * software and their research papers are properly cited.
 * For citation information visit:
 * https://www.cms.bgu.tum.de/en/31-forschung/projekte/456-momentum
 * 
 * However, further rights are not granted.
 * If you need another license or specific rights, contact us!
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package tum.cms.sim.momentum.visualization.model.geometry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import tum.cms.sim.momentum.visualization.controller.CoreController;
import tum.cms.sim.momentum.visualization.controller.CustomizationController;
import tum.cms.sim.momentum.visualization.handler.SelectionHandler.SelectionStates;
import tum.cms.sim.momentum.visualization.model.CustomizationModel;
import tum.cms.sim.momentum.visualization.model.VisibilitiyModel;

public class EdgeModel extends ShapeModel {
	
	private CustomizationModel customizationModel = null;
	
	private VertexModel vertexLeft = null;
	private VertexModel vertexRight = null;
	
	private Line edgeShape = null;
	
	public EdgeModel(CoreController coreController,
			CustomizationController customizationController,
			VisibilitiyModel visibilitiyModel,
			VertexModel vertexLeft,
			VertexModel vertexRight) {
		
		this.customizationModel = customizationController.getCustomizationModel();
		
		this.vertexLeft = vertexLeft;
		this.vertexRight = vertexRight;
		
		edgeShape = new Line(vertexLeft.getVertexShape().getCenterX(),
				vertexLeft.getVertexShape().getCenterY(),
				vertexRight.getVertexShape().getCenterX(),
				vertexRight.getVertexShape().getCenterY());
		
	
		edgeShape.strokeProperty().bind(customizationController.getCustomizationModel().graphColorProperty());
		edgeShape.visibleProperty().bind(visibilitiyModel.graphVisibilityProperty());
		edgeShape.strokeWidthProperty().bind(customizationController.getCustomizationModel().edgeThicknessProperty());
		edgeShape.setTranslateZ(0.001 * coreController.getCoreModel().getResolution());
	}
	
	public Line getEdgeShape() {
		
		return this.edgeShape;
	}

	@Override
	public void setVisibility(boolean isVisible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeSelectionMode(SelectionStates selectionState) {
		
		this.edgeShape.strokeProperty().unbind();
		this.vertexLeft.getVertexShape().fillProperty().unbind();
		this.vertexRight.getVertexShape().fillProperty().unbind();
		
		switch(selectionState) {
		
		case NotSelected:
			this.edgeShape.strokeProperty().bind(customizationModel.graphColorProperty());
			this.vertexLeft.getVertexShape().fillProperty().bind(customizationModel.graphColorProperty());
			this.vertexRight.getVertexShape().fillProperty().bind(customizationModel.graphColorProperty());
			break;
			
		case Selected:
			this.edgeShape.strokeProperty().bind(customizationModel.selectedColorProperty());
			this.vertexLeft.getVertexShape().fillProperty().bind(customizationModel.selectedColorProperty());
			this.vertexRight.getVertexShape().fillProperty().bind(customizationModel.selectedColorProperty());
			this.edgeShape.toFront();
			this.vertexLeft.getVertexShape().toFront();
			this.vertexRight.getVertexShape().toFront();
			break;
		}
	}

	@Override
	public String getIdentification() {

		return new String("L:" + this.vertexLeft.getIdentification() + "/R:" + this.vertexRight.getIdentification());
	}

	@Override
	public List<Node> getClickableShapes() {
		
		ArrayList<Node> clickableShapes = new ArrayList<Node>();
		clickableShapes.add(this.edgeShape);
		
		return clickableShapes;
	}

	@Override
	public LinkedHashMap<String, String> getDataProperties() {

		LinkedHashMap<String, String> details = new LinkedHashMap<>();
		
		details.put(ShapeModel.nameDetails, this.getIdentification());
		details.put(ShapeModel.leftVertex, this.vertexLeft.getIdentification());
		details.put(ShapeModel.rightVertex, this.vertexRight.getIdentification());
		
		return details;
	}

}
