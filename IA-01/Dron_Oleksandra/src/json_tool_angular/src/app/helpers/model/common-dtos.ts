export interface User {
  id: string;
  email: string;
  password: string;
  firstname: string;
  lastname: string;
  role: ROLE;
}

enum ROLE {
  ROLE_USER,
  ROLE_ADMIN
}

export interface JsonSchema {
  id: string;
  title: string;
  description: string;
  json: string;
  createdDate: Date;
}
