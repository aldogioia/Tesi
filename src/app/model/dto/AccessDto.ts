export class AccessDto {
  accessRole: string;
  firstAccess: boolean;
  constructor(data: any) {
      this.firstAccess = data.isFirstAccess;
      this.accessRole = data.accessRole;
  }
}
