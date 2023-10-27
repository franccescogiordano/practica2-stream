package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream()
                .filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        result.forEach((key, jugadores) -> {
            System.out.println("\n");
            System.out.println(key + ": ");
            jugadores.forEach(System.out::println);
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> list = CsvUtilFile.getPlayers();

        var mejorJugador = list.stream()
                .filter(j -> j.getNational().equals("France"))
                .max(Comparator.comparing(Player::getWinners));

        System.out.println(mejorJugador);
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<String>> clubesporNacionalidad = list.stream()
                .collect(Collectors.groupingBy(Player::getNational,
                        Collectors.mapping(Player::getClub, Collectors.toList())));

        clubesporNacionalidad.forEach((nationality, clubs) -> {
            System.out.println("* País : "+nationality + " -> ");
            clubs.forEach(club -> System.out.println("\t- Club : "+club));
        });

    }

    @Test
    void clubConElMejorJugador(){
        List<Player> lista = CsvUtilFile.getPlayers();
        Optional<Player> mejorJugador = lista
                .stream()
                .max(Comparator.comparing(Player::getWinners));
        System.out.println("Club: "
                + mejorJugador.get().getClub()
                +", Name: "+ mejorJugador.get().getName()
                + ", Winners:" + mejorJugador.get().getWinners());
    }

    @Test
    void ElMejorJugador(){
        List<Player> lista = CsvUtilFile.getPlayers();
        Optional<Player> mejorJugador = lista.stream().max(Comparator.comparing(Player::getWinners));
        System.out.println("Jugador: "
                + mejorJugador.get().getName());
    }

    @Test
    void mejorJugadorSegunNacionalidad(){

            List<Player> list = CsvUtilFile.getPlayers();
            var newList = list.stream()
                    .collect(Collectors.groupingBy(Player::getNational,
                            Collectors.maxBy(Comparator.comparing(Player::getWinners))));

            newList.forEach((nacionalidad, jugador) -> {
                if (jugador.isPresent()) {
                    System.out.println("Mejor jugador de " + nacionalidad + ": " + jugador.get().getName());
                }
            });
        }



}
