package cz.schlosserovi.tomas.drooms.strategy.domain.collectibles;

import java.util.List;

import org.drooms.api.Collectible;
import org.drooms.api.Edge;
import org.drooms.api.Node;

import cz.schlosserovi.tomas.drooms.strategy.domain.WithPosition;

public class PositionedCollectible extends WithPosition {
    private static final Node NULL_NODE = Node.getNode(0, 0);
    private static final Edge NULL_EDGE = new Edge(NULL_NODE, Node.getNode(0, 1));
    private final Collectible collectible;
    private List<Edge> path;

    public PositionedCollectible(Collectible collectible, Node position) {
        super(position);
        this.collectible = collectible;
    }

    public Collectible getCollectible() {
        return collectible;
    }

    public int getDistance() {
        return path == null ? Integer.MAX_VALUE : path.size();
    }

    public List<Edge> getPath() {
        return path;
    }

    public Edge getFirstStep() {
        if (path == null || path.size() < 1) return NULL_EDGE;
        return path.get(0);
    }
    
    public void setPath(List<Edge> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("PositionedCollectible[collectible='%s', distance='%s']", collectible, getDistance());
    }
}
