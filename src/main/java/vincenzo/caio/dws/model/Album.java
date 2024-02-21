package vincenzo.caio.dws.model;

import java.util.List;

public record Album(String id, String name, String image, String releasedDate, String band, List<Track> tracks) {
}
