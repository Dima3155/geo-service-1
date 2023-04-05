package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {

    private GeoServiceImpl geoService;
    private static final String MOSCOW_IP = "172.0.32.11";
    private static final String NEW_YORK_IP = "96.44.183.149";

    @BeforeEach
    void setUp() {
        geoService = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void geoTestWith_Ip(String ip, Location location) {
        Assertions.assertEquals(location.getCountry(), geoService.byIp(ip).getCountry());
        Assertions.assertEquals(location.getCity(), geoService.byIp(ip).getCity());
        Assertions.assertEquals(location.getStreet(), geoService.byIp(ip).getStreet());
        Assertions.assertEquals(location.getBuilding(), geoService.byIp(ip).getBuilding());
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.", new Location("New York", Country.USA, null,  0))
        );
    }
}
