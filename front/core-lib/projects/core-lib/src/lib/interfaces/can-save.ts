export interface CanSave{

  isDirty(): boolean;

  save(): void;
}
