package dogbook.mappers;

import dogbook.model.Event;
import dogbook.model.EventUserRelations;
import dogbook.model.User;
import dogbook.model.UserEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventUserMapper {

    @Mapping(target="id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target="userName", source ="user.fullName")
    @Mapping(target="profilePhotoUrl", source ="user.profilePhotoUrl")
    @Mapping(target="eventId", source ="event.id")
    @Mapping(target="goingStatus", source="eventUserRelations.goingStatus")
    @Mapping(target = "eventAccessLevel", source="eventUserRelations.eventaccessLevel")
    @Mapping(target = "eventInvitedStatus", source = "eventUserRelations.eventInvitedStatus")

    UserEventDTO mapFromUserEventAndEventUserRelations(User user, Event event, EventUserRelations eventUserRelations);
}
