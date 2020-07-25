function () {
    var query =
        'query FindAllPlayers {\
            FindAllPlayers {\
                    ...Player\
                }\
                }';

    var fragments = [
        karate.call('classpath:fragments/FragmentPlayer.js').Player()
    ];
    return karate.read('classpath:helper.js')(query, fragments);
}