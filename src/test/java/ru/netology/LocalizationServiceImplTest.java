package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class LocalizationServiceImplTest {

    private LocalizationServiceImpl localizationService;

    @BeforeEach
    void setLocalizationService() {
        localizationService = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void locateRussia(String greeting, Country country) {
        Assertions.assertEquals(greeting, localizationService.locale(country));
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of("Добро пожаловать", Country.RUSSIA),
                Arguments.of("Welcome", Country.GERMANY),
                Arguments.of("Welcome", Country.USA)
        );
    }
}
