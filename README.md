# ChatMaster

/whisper \
/group \
/leave 


### whisper - one on one chat
This command sets the target of your next messages to a single player. It toggles a boolean
in mongodb and the next messages will be sent to the target player.

### group - multiple player chat
A group can be any string and anyone can join it. The group is saved in mongodb and the next messages will be sent to all players in the group.

### leave - reset to regular chat
This command just resets the group to the default values and the chat will function as regular minecraft chat.



