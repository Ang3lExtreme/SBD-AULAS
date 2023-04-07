/*exe1*/
desc uscensus

select count(*) from uscensus
SELECT * FROM uscensus WHERE id=9000;

explain plan set statement_id='q1' into plan_table for
SELECT * FROM uscensus WHERE id=9000;

desc plan_table

 analyze table uscensus compute statistics;

select * from plan_table

SELECT sql_text, elapsed_time
FROM V$SQLAREA
WHERE sql_text like 'SELECT * FROM uscensus WHERE id=9000'

/*exer 2*/
ALTER TABLE uscensus
    ADD CONSTRAINT pk_uscensus PRIMARY KEY (id)

SELECT /*+NO_INDEX(uscensus pk_uscensus)*/ * FROM uscensus WHERE id=9000
SELECT /*+INDEX(uscensus pk_uscensus)*/ * FROM uscensus WHERE id = 9000;

/*exer 3*/

select * from uscensus

/*exe5*/
CREATE BITMAP INDEX occup
ON uscensus(occupation);

SELECT * FROM uscensus WHERE occupation = 'Sales' ;

SELECT *
FROM uscensus
WHERE occupation = 'Engineer' ;

/*EXE6*/
CREATE BITMAP INDEX educ
ON uscensus(education);

SELECT COUNT(*) FROM uscensus WHERE education = 'Preschool'

/*exe7*/
SELECT *
FROM uscensus
WHERE sex = 'Female' AND country = 'Scotland' AND income = '>50K';

EXPLAIN PLAN FOR
SELECT /*+INDEX(uscensus idx_uscensus)*/ *
FROM uscensus
WHERE sex = 'Female' AND country = 'Scotland' AND income = '>50K';

/*Plan hash value: 3607958238

------------------------------------------------------------------------------
| Id  | Operation         | Name     | Rows  | Bytes | Cost (%CPU)| Time     |
------------------------------------------------------------------------------
|   0 | SELECT STATEMENT  |          |   194 | 21534 |   172   (2)| 00:00:01 |
|*  1 |  TABLE ACCESS FULL| USCENSUS |   194 | 21534 |   172   (2)| 00:00:01 |
------------------------------------------------------------------------------

Predicate Information (identified by operation id):
---------------------------------------------------

   1 - filter("COUNTRY"='Scotland' AND "SEX"='Female' AND
              "INCOME"='>50K')*/

SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);

CREATE INDEX idx_uscensus ON uscensus(sex, country, income);

CREATE BITMAP INDEX bmap_uscensus ON uscensus(sex, country, income);
EXPLAIN PLAN FOR
SELECT  *
FROM uscensus
WHERE sex = 'Female' AND country = 'Scotland' AND income = '>50K';


/*EXER8 is just to understand things in a complex way*/

