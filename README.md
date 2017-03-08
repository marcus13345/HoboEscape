# HoboEscape

This originally was a game idea, in which you were a hobo running away from something, finite runner style, but turned in to a place to test out stack based rooms and creating a custom filetype with the level information.

## Room Transitions

Room transitions are achieved by adding the destination room to the stack, then creating a dynamic transition room with the source and destination rooms as parameters. when the transition has finished, it will pop itself, leaving only the destination room. The lifecycle is as follows.

    Source
    Source -> Desitnation -> [Source into Destination]
    Source -> Destination

For going in reverse, the lifecycle happens a little different

    Destination -> Source
    Destination -> [Source into Destination]
    Destination
