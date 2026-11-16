CREATE INDEX IF NOT EXISTS idx_students_school_id ON students (school_id);
CREATE INDEX IF NOT EXISTS idx_students_school_admission_number ON students (school_id, admission_number);

CREATE INDEX IF NOT EXISTS idx_attendance_school_time ON attendance_events (school_id, event_time DESC);
CREATE INDEX IF NOT EXISTS idx_attendance_school_student_time ON attendance_events (school_id, student_id, event_time DESC);
