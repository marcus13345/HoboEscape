# HoboEscape

This is a test of a stack based room system. when running, the title will display the current room stack. a stack is used so that the previous room is always kept in memory with indefinite depth.

## Room Transitions

Room transitions are achieved by adding the destination room to the stack, then creating a dynamic transition room with the source and destination rooms as parameters. when the transition has finished, it will pop itself, leaving only the destination room. The lifecycle is as follows.

    Source
    Source -> Desitnation -> [Source into Destination]
    Source -> Destination

For going in reverse, the lifecycle happens a little different

    Destination -> Source
    Destination -> [Source into Destination]
    Destination
