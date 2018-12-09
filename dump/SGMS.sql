	select PASSWORD from user where name='hxs7975';
update user set LOGIN_ATTEMPTS=0;
update user set password='XOnPiuUCGEVfCa8T355R5Vu6gUHK6kQ6SWXwdXR7bO8=$cj9akCA/vPghqQW5Szfdw0YjUkknARK0afevPvF161M=';
select * from role;
select * from Student;

select * from student_subject;

SELECT * FROM SGMS.professor_subject ps 
inner join subject s on ps.subject_id=s.subject_id 
inner join semester sem on ps.semester_id=sem.semester_id;

select distinct ps.semester_id,s.name from professor_subject ps inner join semester s on ps.semester_id=s.semester_id where ps.professor_id=1; 
select count(ps.SEMESTER_ID) as count,ps.semester_id,s.name from  professor_subject ps inner join semester s on ps.semester_id=s.semester_id where ps.professor_id=1 group by ps.semester_id; 


select ss.STUDENT_SUBJECT_ID,ss.GRADE,s.*
 FROM SGMS.student_subject ss inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id
inner join student s on s.student_id=ss.student_id where ps.professor_subject_id=1 and ps.professor_id=1;


UPDATE student_subject INNER JOIN professor_subject 
       ON student_subject.professor_subject_id=professor_subject.professor_subject_id
SET GRADE = 'H'  WHERE student_subject.student_subject_id=1 and professor_subject.professor_subject_id=1;

SELECT count(sem.name) as C,sem.NAME,sem.SEMESTER_ID
 FROM SGMS.student_subject ss 
inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id
inner join subject s on s.subject_id=ps.subject_id
inner join semester sem on sem.semester_id=ps.semester_id where ss.student_id=1 group by sem.Name;


SELECT ss.GRADE,s.NAME,p.NAME as PNAME
 FROM SGMS.student_subject ss 
inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id
inner join subject s on s.subject_id=ps.subject_id
inner join semester sem on sem.semester_id=ps.semester_id 
inner join professor p on p.professor_id=ps.professor_id where ss.student_id=1 and sem.semester_id=1;






