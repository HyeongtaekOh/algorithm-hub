-- 코드를 입력하세요
# SELECT ANIMAL_ID, NAME, DURATION
# FROM (SELECT ai.ANIMAL_ID, ai.NAME, DATEDIFF(ao.DATETIME, ai.DATETIME) as DURATION
#      FROM animal_ins as ai right join animal_outs as ao
#       ON ai.animal_id = ao.animal_id
#      ORDER BY DURATION desc) as sq
# LIMIT 2

SELECT ai.ANIMAL_ID, ai.NAME
FROM animal_ins as ai 
RIGHT JOIN animal_outs as ao
ON ai.animal_id = ao.animal_id
ORDER BY DATEDIFF(ao.DATETIME, ai.DATETIME) desc
LIMIT 2