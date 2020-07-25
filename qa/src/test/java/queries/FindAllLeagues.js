function () {
    var query =
        'query FindAllLeagues {\
            FindAllLeagues {\
                    ...League\
                }\
                }';

    var fragments = [
        karate.call('classpath:fragments/FragmentLeague.js').League()
    ];
    return karate.read('classpath:helper.js')(query, fragments);
}