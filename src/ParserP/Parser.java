package ParserP;


import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;

//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";










public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character
    private ArchController lexico;

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short VOID=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,    4,    4,    7,    7,
    8,    9,    9,    6,    6,    5,    5,    5,    5,   12,
   10,   14,   14,   15,   16,   16,   11,   13,   18,   18,
   18,   19,   19,   19,   20,   20,   20,   20,   17,   21,
   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    1,    1,    4,    1,    1,    2,    1,    3,    1,
    8,    1,    1,    1,    1,    1,    1,    1,    1,    5,
    8,    2,    1,    4,    1,    1,    8,    4,    3,    3,
    1,    3,    3,    1,    1,    2,    1,    1,    3,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   12,   15,   14,    0,    0,   13,    0,    0,
    1,    2,    3,    5,    6,    0,    8,    0,   16,   17,
   18,   19,    0,    0,    0,    0,    0,   10,    0,    0,
   35,   37,   38,    0,    0,    0,   34,    0,    0,    0,
    0,    0,    0,    0,   36,   28,    0,    0,    0,    0,
    0,   43,   44,   45,   40,   41,   42,    0,    0,    0,
    4,    9,    0,    0,    0,   32,   33,    0,    0,   20,
    0,    0,    0,   25,   26,    0,   23,    0,    0,    0,
    0,   22,    0,    0,   27,   21,    0,   11,   24,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   14,   15,   16,   29,   17,   18,   19,
   20,   21,   22,   76,   77,   78,   38,   35,   36,   37,
   58,
};
final static short yysindex[] = {                      -121,
 -263,    6,    0,    0,    0,   21,   22,    0, -121,    0,
    0,    0,    0,    0,    0, -244,    0, -238,    0,    0,
    0,    0,  -45,  -45, -200, -223, -222,    0,    5,   25,
    0,    0,    0, -194,   13,  -20,    0,   26,  -24,   29,
   30,  -53, -191,   33,    0,    0,  -45,  -45,  -45,  -45,
 -121,    0,    0,    0,    0,    0,    0,  -45,   31,  -47,
    0,    0,  -46,  -20,  -20,    0,    0, -189,    8,    0,
 -229, -121, -121,    0,    0, -122,    0, -188,  -43, -185,
   36,    0, -121, -187,    0,    0,   40,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -32,  -27,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -4,    0,   60,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    0,    0,  -14,   -8,   10,
    0,
};
final static int YYTABLESIZE=278;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
    7,    9,   81,   31,   27,   31,   31,   31,   29,   39,
   29,   29,   29,   30,   23,   30,   30,   30,   47,   28,
   48,   49,   31,   31,   31,   30,   50,   29,   29,   29,
   74,   75,   30,   30,   30,   55,   57,   56,   64,   65,
   41,    1,    2,   69,    7,   24,   68,    3,    4,    5,
   47,    6,   48,    7,    8,   47,   46,   48,   66,   67,
   25,   26,   40,   43,   44,   45,   51,   79,   80,   59,
   60,   61,   62,   63,   70,   71,   72,   73,   87,   86,
   83,   84,   85,   89,   39,   88,   42,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   74,   75,    0,
    0,    0,    1,    2,    0,    0,    0,    0,    3,    4,
    5,    0,    6,    0,    7,    8,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   31,   32,    0,    0,   33,   31,
   31,   31,    0,    0,   29,   29,   29,    0,    0,   30,
   30,   30,   52,   53,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    7,    7,    0,    7,    7,    0,
    7,    7,    7,    0,    7,    0,    7,    7,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,  123,  125,   41,    9,   43,   44,   45,   41,   24,
   43,   44,   45,   41,  278,   43,   44,   45,   43,  264,
   45,   42,   60,   61,   62,  264,   47,   60,   61,   62,
  260,  261,   60,   61,   62,   60,   61,   62,   47,   48,
  264,  264,  265,   58,   44,   40,   51,  270,  271,  272,
   43,  274,   45,  276,  277,   43,   44,   45,   49,   50,
   40,   40,  263,   59,   40,  260,   41,   72,   73,   41,
   41,  125,  264,   41,   44,  123,  123,  267,   83,   44,
  269,  125,  268,   44,   41,  273,   27,   76,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  260,  261,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,   -1,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  260,  261,   -1,   -1,  264,  257,
  258,  259,   -1,   -1,  257,  258,  259,   -1,   -1,  257,
  258,  259,  257,  258,  259,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  264,  265,   -1,  267,  268,   -1,
  270,  271,  272,   -1,  274,   -1,  276,  277,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","VOID",
"\":=\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque_sentencias",
"bloque_sentencias : bloque_lista_sentencias",
"bloque_sentencias : sentencia",
"bloque_lista_sentencias : '{' bloque_sentencias sentencia '}'",
"sentencia : declaracion",
"sentencia : ejecucion",
"declaracion : tipo lista_de_variables",
"declaracion : clousure",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"clousure : tipofuncion ID '(' ')' '{' bloque_sentencias '}' RETURN",
"tipofuncion : FUN",
"tipofuncion : VOID",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')' ','",
"control : CASE '(' ID ')' '{' lista_acciones '}' ','",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte DO bloque_sentencias ','",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"seleccion : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"asignacion : ID \":=\" expresion ','",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : CTE_D",
"factor : '-' CTE_D",
"factor : CTE_USLINTEGER",
"factor : ID",
"condicion : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 7:
//#line 41 "gramaticaHH.Y"
{ }
break;
case 8:
//#line 42 "gramaticaHH.Y"
{}
break;
case 9:
//#line 45 "gramaticaHH.Y"
{}
break;
case 10:
//#line 47 "gramaticaHH.Y"
{}
break;
case 12:
//#line 51 "gramaticaHH.Y"
{}
break;
case 13:
//#line 52 "gramaticaHH.Y"
{}
break;
case 14:
//#line 55 "gramaticaHH.Y"
{}
break;
case 15:
//#line 56 "gramaticaHH.Y"
{}
break;
case 28:
//#line 81 "gramaticaHH.Y"
{}
break;
case 29:
//#line 84 "gramaticaHH.Y"
{}
break;
case 30:
//#line 85 "gramaticaHH.Y"
{}
break;
case 31:
//#line 86 "gramaticaHH.Y"
{}
break;
case 32:
//#line 90 "gramaticaHH.Y"
{}
break;
case 33:
//#line 91 "gramaticaHH.Y"
{}
break;
case 34:
//#line 92 "gramaticaHH.Y"
{}
break;
case 35:
//#line 96 "gramaticaHH.Y"
{}
break;
case 36:
//#line 97 "gramaticaHH.Y"
{}
break;
case 37:
//#line 98 "gramaticaHH.Y"
{}
break;
case 38:
//#line 99 "gramaticaHH.Y"
{}
break;
case 39:
//#line 102 "gramaticaHH.Y"
{}
break;
case 40:
//#line 104 "gramaticaHH.Y"
{}
break;
case 41:
//#line 105 "gramaticaHH.Y"
{}
break;
case 42:
//#line 106 "gramaticaHH.Y"
{}
break;
case 43:
//#line 107 "gramaticaHH.Y"
{}
break;
case 44:
//#line 108 "gramaticaHH.Y"
{}
break;
case 45:
//#line 109 "gramaticaHH.Y"
{}
break;
//#line 558 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser(ArchController lexico)
{
  this.lexico= lexico;
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}

public int yylex(){
    Token token = this.lexico.getToken();
    
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}
//###############################################################


}
//################### END OF CLASS ##############################
