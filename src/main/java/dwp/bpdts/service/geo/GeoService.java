package dwp.bpdts.service.geo;

import dwp.bpdts.model.User;

public interface GeoService {
    double LONDON_LATITUDE = 51.5074;
    double LONDON_LONGITUDE = -0.1278;
    Double getUserDistance(User user);
}
