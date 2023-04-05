package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MessageSenderImplTest {

    private static final String MOSCOW_IP = "172.0.32.11";
    private static final String NEW_YORK_IP = "96.44.183.149";

    @Mock
    private GeoServiceImpl geoService;
    @Mock
    private LocalizationServiceImpl localizationService;

    private MessageSenderImpl messageSender;

    @BeforeEach
    void setUp() {
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    void sendRussianIp() {
        String russianIp = "172.";
        Mockito.when(geoService.byIp(Mockito.startsWith(russianIp))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        String actualResult = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, russianIp));
        String expectedResult = "Добро пожаловать";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void sendNewYorkIp() {
        Mockito.when(geoService.byIp(Mockito.eq(NEW_YORK_IP))).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String actualResult = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, NEW_YORK_IP));
        String expectedResult = "Welcome";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void sendMoscowIp() {
        Mockito.when(geoService.byIp(Mockito.eq(MOSCOW_IP))).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        String actualResult = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, MOSCOW_IP));
        String expectedResult = "Добро пожаловать";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void sendUsaIp() {
        String usaIp = "96.";
        Mockito.when(geoService.byIp(Mockito.startsWith(usaIp))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String actualResult = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, usaIp));
        String expectedResult = "Welcome";
        Assertions.assertEquals(expectedResult, actualResult);
    }


}
