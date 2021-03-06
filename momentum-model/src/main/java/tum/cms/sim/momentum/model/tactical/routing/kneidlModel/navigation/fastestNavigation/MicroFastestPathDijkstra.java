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

package tum.cms.sim.momentum.model.tactical.routing.kneidlModel.navigation.fastestNavigation;

import java.util.Set;

import tum.cms.sim.momentum.data.agent.pedestrian.types.IRichPedestrian;
import tum.cms.sim.momentum.model.tactical.routing.kneidlModel.navigation.KneidlDirectWeightCalculator;
import tum.cms.sim.momentum.model.tactical.routing.kneidlModel.navigation.MicroRoutingAlgorithm;
import tum.cms.sim.momentum.utility.graph.Graph;
import tum.cms.sim.momentum.utility.graph.Path;
import tum.cms.sim.momentum.utility.graph.Vertex;
import tum.cms.sim.momentum.utility.graph.pathAlgorithm.ShortestPathAlgorithm;

public class MicroFastestPathDijkstra extends MicroRoutingAlgorithm {

	private ShortestPathAlgorithm algorithm = null;
	private KneidlDirectWeightCalculator weightCalculator = null;
	
	public MicroFastestPathDijkstra(KneidlDirectWeightCalculator weightCalculator,
			IRichPedestrian currentPedestrian) {
		
		super(currentPedestrian);
		
		this.weightCalculator = weightCalculator;
		this.weightCalculator.setCurrentPedestrian(currentPedestrian);
		this.algorithm = new ShortestPathAlgorithm(weightCalculator);
	}
	
	@Override
	public Path route(Graph graph, 
			Set<Vertex> pedestrainVisitiedVertices, 
			Vertex previousVertex, 
			Vertex pedestrianPosition,
			Vertex destination) {
		
		this.weightCalculator.setCurrentPerception(this.currentPerception);
		return this.algorithm.calculateShortestPath(graph, pedestrianPosition, destination);
	}
	
	@Override
	public void initializePedestrianWeightsForTarget(Graph graph, Vertex target) {

		this.weightCalculator.initalizeWeights(graph);
	}
	
	@Override
	public void removePedestrianWeightsForTarget(Graph graph,Vertex target) {
		
		this.weightCalculator.removeWeights(graph);
	}
}
