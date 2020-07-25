function () {
    var query =
        'query FindAllTeams {\
            FindAllTeams {\
                    ...Team\
                }\
                }';

    var fragments = [
        karate.call('classpath:fragments/FragmentTeam.js').Team()
    ];
    return karate.read('classpath:helper.js')(query, fragments);
}