CREATE OR REPLACE VIEW public.security_group_annotations AS
  SELECT a.annotationid as id, c.collectionid, sg.id as groupid
  FROM security_group sg
         INNER JOIN annotation_group_membership agm ON agm.group_id = sg.id
         INNER JOIN annotation a ON a.id = agm.annotation_id
         INNER JOIN annotation_collection c on c.id = a.collectionid;

CREATE OR REPLACE VIEW public.security_group_users AS
  SELECT su.uid, su.security_user_id, sg.id as groupid
  FROM security_group sg
         INNER JOIN security_user_group_membership sugm ON sugm.group_id = sg.id
         INNER JOIN security_user su ON su.id = sugm.user_id
  UNION ALL
  SELECT su.uid, su.security_user_id, sg.id
  FROM security_group sg
         INNER JOIN security_user su ON su.id = sg.owner_id;
