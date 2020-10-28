package dwp.bpdts.service.geo;

import dwp.bpdts.model.User;
import lombok.extern.slf4j.Slf4j;
import net.sf.geographiclib.Geodesic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoServiceImpl implements GeoService {

    private final Geodesic geo;
    public static final int ONE_THOUSAND = 1000;
    public static final double MILES_TO_KM = 1.60934;

    public GeoServiceImpl() {
        geo = new Geodesic(Geodesic.WGS84.EquatorialRadius(),
                Geodesic.WGS84.Flattening());
    }

    /**
     * Calculate distance between two positions
     *
     * @param user user-info
     * @return distance in miles
     */
    @Override
    public Double getUserDistance(User user) {

        double distanceInMetres = geo.Inverse(GeoService.LONDON_LATITUDE, GeoService.LONDON_LONGITUDE,
                user.getLatitude(), user.getLongitude()).s12;
        double distanceInKm = distanceInMetres / ONE_THOUSAND;
        return distanceInKm * MILES_TO_KM;
    }
}

