project(qqd)
cmake_minimum_required(VERSION 3.24)
find_package(PkgConfig)
pkg_search_module(DBUS REQUIRED dbus-1)
add_library(qqd SHARED
        )
include_directories(
        src
        ${DBUS_INCLUDE_DIRS}
        )
target_link_libraries(qqd ${DBUS_LIBRARIES})