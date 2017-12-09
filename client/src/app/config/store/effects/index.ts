import {OperatorManagePageEffects} from './operator-manage-page.effects';
import {ReceiveT001ManagePageEffects} from './receive-t001-manage-page.effects';
import {ReceiveT001lManagePageEffects} from './receive-t001l-manage-page.effects';
import {TransCorpManagePageEffects} from './trans-corp-manage-page.effects';
import {WharfT001lManagePageEffects} from './wharf-t001l-manage-page.effects';

export const featureEffects = [
  ReceiveT001ManagePageEffects,
  ReceiveT001lManagePageEffects,
  WharfT001lManagePageEffects,
  TransCorpManagePageEffects,
  OperatorManagePageEffects,
];
