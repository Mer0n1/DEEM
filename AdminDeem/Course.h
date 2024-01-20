#ifndef COURSEOFFACULTY_H
#define COURSEOFFACULTY_H

#include "Event.h"
#include "Group.h"


struct Course {
    int course;
    QList<Group*> groups;
    QList<Event*> events;
};

#endif // COURSEOFFACULTY_H
