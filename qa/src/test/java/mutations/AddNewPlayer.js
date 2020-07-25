function () {
    var mutation =
        'mutation AddNewPlayer($first_name:String!, $last_name:String!,$team_id:Int!) {\
            AddNewPlayer (first_name:$first_name, last_name:$last_name, team_id:$team_id){\
                    ...Player\
                }\
                }';

    var fragments = [
        karate.call('classpath:fragments/FragmentPlayer.js').Player()
    ];
    return karate.read('classpath:helper.js')(mutation, fragments);
}