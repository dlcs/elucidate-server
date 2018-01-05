#!/bin/bash
set -x

# This script will only operate if you pass it "create" as its first parameter. This is a safety mechanism.
# The second parameter is the connection string to be used to contact PostgreSQL
# e.g.
#   ./create-using-docker.sh create postgresql://username:password@host:port

if [ "$1" == "create" ]; then
	echo "This script will use Docker to run various .sql files into the PostgreSQL instance."

	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/postgres -a -f /scripts/group_roles/annotations_role.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/postgres -a -f /scripts/login_roles/annotations_user.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/postgres -a -f /scripts/annotations.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_collection.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_body.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_target.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent_email.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent_email_sha1.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent_homepage.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent_name.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_agent_type.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_selector.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_history.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/tables/annotation_temporal.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_body_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_collection_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_creator_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_css_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_data_position_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_fragment_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_svg_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_target_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_text_position_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_text_quote_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_xpath_selector_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_history_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_generator_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/views/annotation_temporal_get.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_body_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_body_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_collection_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_creator_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_creator_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_css_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_css_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_data_position_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_data_position_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_fragment_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_fragment_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_body.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_creator.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_target.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_svg_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_svg_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_target_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_target_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_text_position_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_text_position_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_text_quote_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_text_quote_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_update.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_xpath_selector_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_xpath_selector_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_history_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_history_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_generator_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_generator_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_generator.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_temporal.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_temporal_create.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_temporal_delete.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_body.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/functions/annotation_search_by_target.sql
	docker run -t -i --rm --volume=$(pwd):/scripts postgres:latest psql $2/annotations -a -f /scripts/permissions/annotations_permissions.sql
fi
