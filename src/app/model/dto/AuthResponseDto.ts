import {Professor} from "../Professor";

export class AuthResponseDto {
  accessToken: string;
  refreshToken: string;
  professor: Professor;

  constructor(data: any) {
    this.accessToken = data.token;
    this.refreshToken = data.refreshToken;
    this.professor = data.professor;
  }
}
